namespace UI.Pages;

public partial class IndexPage : ContentPage
{
	public IndexPage()
	{
		InitializeComponent();
	}

    protected override void OnAppearing()
    {
        base.OnAppearing();
        Window.MinimumWidth = 1300;
        Window.MinimumHeight = 700;
    }
}