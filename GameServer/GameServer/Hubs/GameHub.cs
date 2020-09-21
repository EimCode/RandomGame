using GameServer.Helpers;
using Microsoft.AspNetCore.SignalR;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using System.Timers;

namespace GameServer.Hubs
{
    public class GameHub : Hub
    {
        private readonly static ConnectionMapping<string> _connections =
            new ConnectionMapping<string>();

        Random rnd = new Random();
        public override Task OnConnectedAsync()
        {
            Debug.WriteLine("_____________________________________________________________________________________________________");
            return base.OnConnectedAsync();
        }

        public override Task OnDisconnectedAsync(Exception exception)
        {
            Debug.WriteLine("___________________________________________-__-______________________________________________________");

            string nameToRemove = _connections.GetKey(Context.ConnectionId); //Get name of disconnected player

            if (nameToRemove != null)
                _connections.Remove(nameToRemove);

            Tank tankToRemove = Program.tanks.FirstOrDefault(t => t.Name.Equals(nameToRemove));
            if (tankToRemove != null)
            {
                Program.tanks.Remove(tankToRemove);
                Clients.AllExcept(Context.ConnectionId).SendAsync("OpponentLeft", nameToRemove);
            }

            return base.OnDisconnectedAsync(exception);
        }


        public void GetMap()
        {
            //Map map = new Map("map1.txt");
            Clients.Caller.SendAsync("ReceiveMap", Program.map.TileMap);
        }

        public void PlayerJoined(string name)
        {
            _connections.Add(name, Context.ConnectionId);

            int[] Position = Program.map.GetFreePosition();
            Clients.Caller.SendAsync("ReceiveObjects", Position, Program.tanks.ToArray(), Program.utilities.ToArray());
            Program.AddTank(Position, name);

            Clients.AllExcept(Context.ConnectionId).SendAsync("ReceiveOpponentCoords", name, Position);

        }


        public void UpdateCoords(string name, int x, int y)
        {
            int index = Program.MoveTank(x, y, name);

            if (index < 0)
                return;

            for (int i = 0; i < Program.utilities.Count; i++)
            {
                if (Program.tanks[index].X == Program.utilities[i].X && Program.tanks[index].Y == Program.utilities[i].Y)
                {
                    if (Program.utilities[i].Name.Contains("hp"))
                        Program.tanks[index].hp += 20;
                    Clients.All.SendAsync("PickedUtility", Program.tanks[index].Name, Program.utilities[i]);
                    Clients.All.SendAsync("RemoveUtility", Program.utilities[i].X, Program.utilities[i].Y);
                    Program.utilities.Remove(Program.utilities[i]);
                }
            }
            Clients.AllExcept(Context.ConnectionId).SendAsync("UpdatedCoords", name, x, y);
        }

        public void ProjectileCoords(int x, int y, string type, int direction)
        {
            Clients.AllExcept(Context.ConnectionId).SendAsync("SpawnProjectile", x, y, type, direction);
        }
    }
}
