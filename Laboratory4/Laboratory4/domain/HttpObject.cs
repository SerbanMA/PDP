using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace Laboratory4.domain
{
    class HttpObject
    {
        // Socket
        public Socket clientSocket = null;
        public const int bufferSize = 1024;
        public byte[] buffer = new byte[bufferSize];

        public string responseContent = "";

        public int clientId;
        // Server
        public string hostname;
        public string requestPath;
        public IPEndPoint endPoint;

        // Thread
        public ManualResetEvent connect = new ManualResetEvent(false);
        public ManualResetEvent send = new ManualResetEvent(false);
        public ManualResetEvent receive = new ManualResetEvent(false);

    }
}
