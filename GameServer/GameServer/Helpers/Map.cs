using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace GameServer.Helpers
{
    public class Map
    {
        public int[][] TileMap;
        public int Width;
        public int Height;

        public Map(string fileName)
        {
            ReadMap(fileName);
        }

        public void ReadMap(string fileName)
        {
            string[] lines = System.IO.File.ReadAllLines(fileName);
            this.Width = int.Parse(lines[0]);
            this.Height = int.Parse(lines[1]);
            TileMap = new int[Height][];

            for (int i = 2; i < Height+2; i++)
            {
                TileMap[i - 2] = new int[Width];
                string[] lineVar = lines[i].Split(" ");

                for (int j = 0; j < Width; j++)
                {
                    TileMap[i - 2][j] = int.Parse(lineVar[j]);
                }
            }
        }

        public int[] GetFreePosition()
        {
            Random rand = new Random();
            int col = 0, row = 0;
            int rc = -1;
            while (rc != 0)
            {
                col = rand.Next(48);
                row = rand.Next(27);
                rc = TileMap[row][col];
            }
            return new int[] { col, row };
        }

        public void setXY(int x, int y, int value)
        {
            TileMap[y][x] = value;
        }
    }
}
