using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laboratory4.helper
{
    class HttpHelper
    {
        public static readonly int port = 80;

        public static string GetResponseBody(string response)
        {
            string[] data = response.Split(new[] { "\r\n\r\n" }, StringSplitOptions.RemoveEmptyEntries);

            return data.Length > 1 ? data[1] : "";
        }

        public static bool IsResponseHeaderFullyObtained(string response)
        {
            return response.Contains("\r\n\r\n");
        }

        public static int GetContentLengthHeader(string response)
        {
            int contentHeaderLength = 0;
            string[] data = response.Split('\r', '\n');

            foreach (string line in data)
            {
                string[] headerDetails = line.Split(':');

                if (headerDetails[0] == "Content-Length")
                    contentHeaderLength = int.Parse(headerDetails[1]);
            }

            return contentHeaderLength;
        }

        public static string GetRequestString(string hostname, string endpoint)
        {
            return "GET " + endpoint + " HTTP/1.1\r\n" +
                   "Host: " + hostname + "\r\n" +
                   "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36\r\n" +
                   "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,#1#*;q=0.8\r\n" +
                   "Accept-Language: en-US,en;q=0.9,ro;q=0.8\r\n" +
                   "Accept-Encoding: gzip, deflate\r\n" +
                   "Connection: keep-alive\r\n" +
                   "Upgrade-Insecure-Requests: 1\r\n" +
                   "Pragma: no-cache\r\n" +
                   "Cache-Control: no-cache\r\n" +
                   "Content-Length: 0\r\n\r\n";
        }
    }
}
