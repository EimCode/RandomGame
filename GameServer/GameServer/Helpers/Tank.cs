using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace GameServer.Helpers
{
    public class Tank
    {
        public int X, Y;
        public string Name;
        public int hp;

        public Tank(int x, int y, string Name)
        {
            this.X = x;
            this.Y = y;
            this.Name = Name;
            this.hp = 100;
        }
    }
}
