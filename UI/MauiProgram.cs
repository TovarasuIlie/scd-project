using Blazorise;
using Blazorise.Bootstrap5;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.Extensions.Logging;
using UI.AuthenticationProvider;
using UI.Services;

namespace UI
{
    public static class MauiProgram
    {
        public static MauiApp CreateMauiApp()
        {
            var builder = MauiApp.CreateBuilder();
            builder
                .UseMauiApp<App>()
                .ConfigureFonts(fonts =>
                {
                    fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
                });

            builder.Services.AddMauiBlazorWebView();
            builder.Services.AddBlazorise(options =>
                            {
                                options.Immediate = true;
                            })
                            .AddBootstrap5Providers();

            builder.Services.AddScoped<AuthenticationStateProvider, CustomAuthenticationStateProvider>();
            builder.Services.AddAuthorizationCore();

            builder.Services.AddSingleton<AuthenticationService>();
            builder.Services.AddSingleton<DeliveryService>();
            builder.Services.AddSingleton<ManagerService>();
            builder.Services.AddSingleton<PackageService>();

            builder.Services.AddHttpClient("API", httpClient =>
            {
                var baseAddress = DeviceInfo.Platform == DevicePlatform.Android ? "http://192.168.10.2:8081" : "http://localhost:8081";
                httpClient.BaseAddress = new Uri(baseAddress);
            });
#if DEBUG
            builder.Services.AddBlazorWebViewDeveloperTools();
    		builder.Logging.AddDebug();
#endif

            return builder.Build();
        }
    }
}
