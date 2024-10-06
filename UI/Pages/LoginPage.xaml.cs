using UI.ViewModel;

namespace UI.Pages;

public partial class LoginPage : ContentPage
{
    public LoginPage(LoginViewModel loginViewModel)
    {
        InitializeComponent();
        BindingContext = loginViewModel;
    }
    protected override void OnAppearing()
    {
        base.OnAppearing();
        Window.MinimumWidth = 400;
        Window.MaximumWidth = 400;
        Window.MinimumHeight = 600;
        Window.MaximumHeight = 600;
    }

}