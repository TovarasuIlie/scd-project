
using CommunityToolkit.Mvvm.ComponentModel;
using System.Text.Json;
using UI.DTOs;

namespace UI.ViewModel
{
    public partial class ProfileViewModel : ObservableObject
    {
        [ObservableProperty]
        public LoggedUserDTO loggedUser;

        public ProfileViewModel() 
        {
            loggedUser = LoadInfo();
        }

        private LoggedUserDTO LoadInfo()
        {
            var data = SecureStorage.Default.GetAsync("LoggedUser");
            return JsonSerializer.Deserialize<LoggedUserDTO>(data.Result);
        }
    }
}
