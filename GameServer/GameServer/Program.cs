using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using GameServer.Helpers;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;

namespace GameServer
{
    public class Program
    {
        public static Map map = new Map("map1.txt");
        public static List<Tank> tanks = new List<Tank>();
        public static List<Utility> utilities = new List<Utility>();
        public static void Main(string[] args)
        {
            CreateWebHostBuilder(args).Build().Run();

            Debug.WriteLine(map.Width + "----------------------------------------------------------------------------");
        }

        public static void AddTank(int[] Position, string name)
        {
            Program.tanks.Add(new Tank(Position[0], Position[1], name));
            map.setXY(Position[0], Position[1], 4);
        }

        public static int MoveTank(int x, int y, string name)
        {
            int index = tanks.FindIndex(t => t.Name.Equals(name));
            //Debug.WriteLine(name + "-------------------------------------------..........................--------------------------------------------------");
            if (index < 0)
                return index;

            map.setXY(tanks[index].X, tanks[index].Y, 0);

            tanks[index].X = x;
            tanks[index].Y = y;

            map.setXY(tanks[index].X, tanks[index].Y, 4);
            return index;
        }



        public static IWebHostBuilder CreateWebHostBuilder(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>();
    }
}
