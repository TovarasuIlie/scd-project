using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using UI.DTOs;
using UI.Services;

namespace UI.ViewModel
{
    public partial class LoginViewModel : ObservableObject
    {
        [ObservableProperty]
        public LoginRequestDTO loginRequestDTO;

        private readonly AuthService authService;

        public LoginViewModel(AuthService authService)
        {
            this.authService = authService;
            LoginRequestDTO = new();
        }

        [RelayCommand]
        private async Task Login()
        {
            await authService.LoginAsync(loginRequestDTO);
        }
    }
}
