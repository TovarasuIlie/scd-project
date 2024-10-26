using UI.Models;
using UI.ViewModel;

namespace UI.Pages;

public partial class IndexPage : ContentPage
{

	public IndexPage(IndexViewModel indexViewModel)
	{
		InitializeComponent();
        BindingContext = indexViewModel;
	}

    protected override async void OnAppearing()
    {
        base.OnAppearing();
        Window.MinimumWidth = 1300;
        Window.MinimumHeight = 700;
    }

    private void ListView_GoToDetails(object sender, SelectedItemChangedEventArgs e)
    {
        Package selectedPackage = (Package) e.SelectedItem;
        MessagingCenter.Send<IndexPage, int>(this, "deliveryID", selectedPackage.Id);
        Shell.Current.GoToAsync($"{nameof(ViewDeliveryDetailsPage)}");
    }
}