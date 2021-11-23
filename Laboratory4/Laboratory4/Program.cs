using Laboratory4.server;
using System;
using System.Collections.Generic;
using System.Threading;

namespace Laboratory4
{
    class Program
    {
        static void Main(string[] args)
        {
            //List<string> hosts = new List<string> { "en.wikipedia.org/wiki/Alan_Turing", "www.washingtonpost.com/", "google.com" };
            List<string> hosts = new List<string> { "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/progs/srv-begin-end.cs", "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/progs/srv-task.cs", "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/progs/srv-await.cs" };

            DirectCallback.Start(hosts);
            Thread.Sleep(2000);
            Console.WriteLine("--------------------------------------------------");
            SyncTaskMech.Start(hosts);
            Thread.Sleep(2000);
            Console.WriteLine("--------------------------------------------------");
            AsyncTaskMech.Start(hosts);
            Thread.Sleep(100000);
        }
    }
}
