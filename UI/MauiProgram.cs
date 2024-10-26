using Microsoft.Extensions.Logging;
using UI.Pages;
//using UI.Platforms.Android;
using UI.Services;
using UI.ViewModel;

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
                    fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
                });

            builder.Services.AddSingleton<AuthService>();
            builder.Services.AddSingleton<PackageService>();
            builder.Services.AddSingleton<LoginPage>();
            builder.Services.AddSingleton<LoginViewModel>();
            builder.Services.AddSingleton<IndexPage>();
            builder.Services.AddSingleton<IndexViewModel>();
            builder.Services.AddSingleton<ProfilePage>();
            builder.Services.AddSingleton<ProfileViewModel>();
            builder.Services.AddSingleton<ViewDeliveryDetailsPage>();
            builder.Services.AddSingleton<ViewDeliveryDetailsViewModel>();

            builder.Services.AddSingleton<IPlatformHttpMessageHandler>(sp =>
            {
#if ANDROID
                return new Platforms.Android.AndroidHttpMessageHandler();
#else
                return null!;
#endif
            });

            builder.Services.AddHttpClient("custom-httpclient", httpClient =>
            {
                var baseAddress = DeviceInfo.Platform == DevicePlatform.Android ? "http://192.168.0.100:8081" : "http://localhost:8081";
                httpClient.BaseAddress = new Uri(baseAddress);
            });

#if DEBUG
            builder.Logging.AddDebug();
#endif

            return builder.Build();
        }
    }
}
