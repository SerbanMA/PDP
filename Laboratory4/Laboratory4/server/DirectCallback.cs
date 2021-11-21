using Laboratory4.domain;
using Laboratory4.helper;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Laboratory4.server
{
    class DirectCallback
    {
        public static void Start(List<string> HOSTS)
        {
            for (int clientId = 0; clientId < HOSTS.Count; clientId++)
            {
                StartClient(clientId, HOSTS[clientId]);
                Thread.Sleep(500);
            }
        }

        private static void StartClient(int clientId, string host)
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

            httpObject.clientSocket.BeginConnect(httpObject.endPoint, Connect, httpObject);
        }

        private static void Connect(IAsyncResult ar)
        {
            var httpObject = (HttpObject)ar.AsyncState;

            var clientSocket = httpObject.clientSocket;
            var clientId = httpObject.clientId;
            var hostname = httpObject.hostname;

            clientSocket.EndConnect(ar);
            Console.WriteLine("{0} --> Socket connected to {1} ({2})", clientId, hostname, clientSocket.RemoteEndPoint);

            var byteData = Encoding.ASCII.GetBytes(HttpHelper.GetRequestString(httpObject.hostname, httpObject.requestPath));
            httpObject.clientSocket.BeginSend(byteData, 0, byteData.Length, 0, Send, httpObject);
        }

        private static void Send(IAsyncResult ar)
        {
            var httpObject = (HttpObject)ar.AsyncState;

            var clientSocket = httpObject.clientSocket;
            var clientId = httpObject.clientId;

            var bytesSent = clientSocket.EndSend(ar);
            Console.WriteLine("{0} --> Sent {1} bytes to server.", clientId, bytesSent);

            httpObject.clientSocket.BeginReceive(httpObject.buffer, 0, HttpObject.bufferSize, 0, Receive, httpObject);
        }

        private static void Receive(IAsyncResult ar)
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
                    clientSocket.BeginReceive(httpObject.buffer, 0, HttpObject.bufferSize, 0, Receive, httpObject);
                }
                else
                {
                    var responseBody = HttpHelper.GetResponseBody(httpObject.responseContent.ToString());

                    var contentLengthHeaderValue = HttpHelper.GetContentLengthHeader(httpObject.responseContent.ToString());
                    if (responseBody.Length < contentLengthHeaderValue)
                    {
                        clientSocket.BeginReceive(httpObject.buffer, 0, HttpObject.bufferSize, 0, Receive, httpObject);
                    }
                    else
                    {
                        Console.WriteLine(
                            "{0} --> Response received : expected {1} chars in body, got {2} chars (headers + body)",
                            clientId, contentLengthHeaderValue, httpObject.responseContent.Length);

                        clientSocket.Shutdown(SocketShutdown.Both);
                        clientSocket.Close();
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
