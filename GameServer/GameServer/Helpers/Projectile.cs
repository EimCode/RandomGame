using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace GameServer.Helpers
{
    public class Projectile
    {
        int x, y, damage;
        string type;

        public Projectile(int x, int y, string type)
        {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }
}
