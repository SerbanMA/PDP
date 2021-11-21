using Laboratory4.domain;
using Laboratory4.helper;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Laboratory4.server
{
    class AsyncTaskMech
    {
        private static List<string> HOSTS;
        public static void Start(List<string> hosts)
        {
            HOSTS = hosts; 
            var tasks = new List<Task>();

            for (var index = 0; index < HOSTS.Count; index++)
            {
                tasks.Add(Task.Factory.StartNew(DoStart, index));
            }
            Task.WaitAll(tasks.ToArray());
        }

        private static void DoStart(object idObject)
        {
            var clientId = (int)idObject;

            StartClient(clientId, HOSTS[clientId]);
            Thread.Sleep(500);
        }

        private static async void StartClient(int clientId, string host)
        {
            var ipHost = Dns.GetHostEntry(host.Split('/')[0]);
            var ipAddress = ipHost.AddressList[0];
            var endPoint = new IPEndPoint(ipAddress, HttpHelper.port);

            var clientSocket = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

            var httpObject = new HttpObject
            {
                clientSocket = clientSocket,
                hostname = host.Split('/')[0],
                requestPath = host.Contains("/") ? host.Substring(host.IndexOf("/")) : "/",
                endPoint = endPoint,
                clientId = clientId
            };

            await ConnectWrapper(httpObject);
            await SendWrapper(httpObject);
            await ReceiveWrapper(httpObject);

            clientSocket.Shutdown(SocketShutdown.Both);
            clientSocket.Close();
        }

        private static async Task ConnectWrapper(HttpObject httpObject)
        {
            httpObject.clientSocket.BeginConnect(httpObject.endPoint, ConnectCallback, httpObject);

            await Task.FromResult(httpObject.connect.WaitOne());
        }

        private static void ConnectCallback(IAsyncResult ar)
        {
            var httpObject = (HttpObject)ar.AsyncState;

            var clientSocket = httpObject.clientSocket;
            var clientId = httpObject.clientId;
            var hostname = httpObject.hostname;

            clientSocket.EndConnect(ar);
            Console.WriteLine("{0} --> Socket connected to {1} ({2})", clientId, hostname, clientSocket.RemoteEndPoint);

            httpObject.connect.Set();
        }

        private static async Task SendWrapper(HttpObject httpObject)
        {
            var byteData = Encoding.ASCII.GetBytes(HttpHelper.GetRequestString(httpObject.hostname, httpObject.requestPath));
            httpObject.clientSocket.BeginSend(byteData, 0, byteData.Length, 0, SendCallback, httpObject);

            await Task.FromResult(httpObject.send.WaitOne());
        }

        private static void SendCallback(IAsyncResult ar)
        {
            var httpObject = (HttpObject)ar.AsyncState;

            var clientSocket = httpObject.clientSocket;
            var clientId = httpObject.clientId;

            var bytesSent = clientSocket.EndSend(ar);
            Console.WriteLine("{0} --> Sent {1} bytes to server.", clientId, bytesSent);
            
            httpObject.send.Set();
        }

        private static async Task ReceiveWrapper(HttpObject httpObject)
        {
            httpObject.clientSocket.BeginReceive(httpObject.buffer, 0, HttpObject.bufferSize, 0, ReceiveCallback, httpObject);

            await Task.FromResult(httpObject.receive.WaitOne());
        }

        private static void ReceiveCallback(IAsyncResult ar)
        {
            var httpObject = (HttpObject)ar.AsyncState;

            var clientSocket = httpObject.clientSocket;
            var clientId = httpObject.clientId;

            try
            {
                var bytesRead = clientSocket.EndReceive(ar);

                httpObject.responseContent += Encoding.ASCII.GetString(httpObject.buffer, 0, bytesRead);

                if (!HttpHelper.IsResponseHeaderFullyObtained(httpObject.responseContent.ToString()))
                {
                    clientSocket.BeginReceive(httpObject.buffer, 0, HttpObject.bufferSize, 0, ReceiveCallback, httpObject);
                }
                else
                {
                    var responseBody = HttpHelper.GetResponseBody(httpObject.responseContent.ToString());

                    var contentLengthHeaderValue = HttpHelper.GetContentLengthHeader(httpObject.responseContent.ToString());
                    if (responseBody.Length < contentLengthHeaderValue)
                    {
                        clientSocket.BeginReceive(httpObject.buffer, 0, HttpObject.bufferSize, 0, ReceiveCallback, httpObject);
                    }
                    else
                    {
                        Console.WriteLine(
                            "{0} --> Response received : expected {1} chars in body, got {2} chars (headers + body)",
                            clientId, contentLengthHeaderValue, httpObject.responseContent.Length);

                        httpObject.receive.Set();
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }
    }
}
