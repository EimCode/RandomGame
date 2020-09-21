using GameServer.Helpers;
using GameServer.Hubs;
using Microsoft.AspNetCore.SignalR;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace GameServer.Services
{
    public class MapUpdate : BackgroundService
    {
        private Timer _timer;
        private readonly IHubContext<GameHub> hubContext;
        private string[] UtilityArray = new string[] {"ak", "m4", "hp" };
        Random random = new Random();

        public MapUpdate(IHubContext<GameHub> hubContext)
        {
            this.hubContext = hubContext;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            
            _timer = new Timer((x) => SpawnUtility(), null, 0, 1500);
            await Task.CompletedTask;
        }

        public void SpawnUtility()
        {
            int index = random.Next(UtilityArray.Length);
            string name = UtilityArray[index];
            if (Program.tanks.Count > 0 && Program.utilities.Count < 10)
            {
                int[] Position = Program.map.GetFreePosition();
                hubContext.Clients.All.SendAsync("SpawnedUtility", name, Position);
                Debug.WriteLine(Position[0] + " " + Position[1]);
                Program.utilities.Add(new Utility(Position[0], Position[1], name));
            }
        }

        public void SpawnProjectile(string type)
        {

            hubContext.Clients.All.SendAsync("SpawnedProjectile", Program.tanks);
        }

        public override async Task StopAsync(CancellationToken stoppingToken)
        {
            await Task.CompletedTask;
        }
    }
}
