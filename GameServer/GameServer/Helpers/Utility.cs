using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace GameServer.Helpers
{
    public class Utility
    {
        public int X, Y;
        public string Name;

        public Utility(int x, int y, string Name)
        {
            this.X = x;
            this.Y = y;
            this.Name = Name;
        }
    }
}
