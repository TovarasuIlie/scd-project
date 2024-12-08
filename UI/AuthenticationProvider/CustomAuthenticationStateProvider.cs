using Microsoft.AspNetCore.Components.Authorization;
using System.Data;
using System.Security.Claims;
using System.Text.Json;
using UI.Models.Authentication;

namespace UI.AuthenticationProvider
{
    public class CustomAuthenticationStateProvider : AuthenticationStateProvider
    {
        private ClaimsPrincipal anonymous = new ClaimsPrincipal(new ClaimsIdentity());
        public override async Task<AuthenticationState> GetAuthenticationStateAsync()
        {
            try
            {
                string loggedUserString = await SecureStorage.Default.GetAsync("LoggedUser");
                if (string.IsNullOrEmpty(loggedUserString))
                {
                    return await Task.FromResult(new AuthenticationState(anonymous));
                }
                var loggedUser = JsonSerializer.Deserialize<LoggedUser>(loggedUserString);
                if (loggedUser.Expiration > DateTime.UtcNow.Subtract(new DateTime(1970, 1, 1)).TotalSeconds)
                {
                    List<Claim> claims = new List<Claim>()
                    {
                        new Claim(ClaimTypes.NameIdentifier, loggedUser.Id.ToString()),
                        new Claim(ClaimTypes.Email, loggedUser.Email!),
                        new Claim(ClaimTypes.Name, loggedUser.Name!),
                        new Claim(ClaimTypes.Role, loggedUser.Role!),
                        new Claim("Expiration", loggedUser.Expiration.ToString()!),
                        new Claim("Jwt", loggedUser.Jwt!)
                    };

                    var claimsPrincipal = new ClaimsPrincipal(new ClaimsIdentity(claims, "CustomAuth"));

                    return await Task.FromResult(new AuthenticationState(claimsPrincipal));
                }
                else
                {
                    return await Task.FromResult(new AuthenticationState(anonymous));
                }
            }
            catch
            {
                return await Task.FromResult(new AuthenticationState(anonymous));
            }
        }

        public async Task UpdateAuthenticationStateAsync(LoggedUser loggedUser)
        {
            ClaimsPrincipal claimsPrincipal;
            if (!string.IsNullOrEmpty(loggedUser.Id.ToString()))
            {
                string serializeUser = JsonSerializer.Serialize(loggedUser);
                await SecureStorage.Default.SetAsync("LoggedUser", serializeUser);
                List<Claim> claims = new List<Claim>()
                {
                    new Claim(ClaimTypes.NameIdentifier, loggedUser.Id!.ToString()),
                    new Claim(ClaimTypes.Email, loggedUser.Email!),
                    new Claim(ClaimTypes.Name, loggedUser.Name!),
                    new Claim(ClaimTypes.Role, loggedUser.Role!),
                    new Claim("Expiration", loggedUser.Expiration.ToString()!),
                    new Claim("Jwt", loggedUser.Jwt!)
                };
                claimsPrincipal = new ClaimsPrincipal(new ClaimsIdentity(claims));
            }
            else
            {
                SecureStorage.Default.Remove("LoggedUser");
                claimsPrincipal = anonymous;
            }
            NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(claimsPrincipal)));
        }
    }
}
