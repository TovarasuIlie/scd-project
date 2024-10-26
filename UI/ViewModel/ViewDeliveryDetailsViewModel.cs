using CommunityToolkit.Mvvm.ComponentModel;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using UI.Models;
using UI.Pages;
using UI.Services;

namespace UI.ViewModel
{
    public partial class ViewDeliveryDetailsViewModel : ObservableObject, INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler? PropertyChanged;
        private readonly PackageService packageService;

        [ObservableProperty]
        public Package selectedDelivery;
        protected void OnPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public ViewDeliveryDetailsViewModel(PackageService packageService)
        {
            this.packageService = packageService;
            MessagingCenter.Subscribe<IndexPage, int>(this, "deliveryID", (sender, arg) =>
            {
                LoadPackages(arg);
            });
        }

        public async Task LoadPackages(int id)
        {
            selectedDelivery = await packageService.GetDelivaryByID(id);
        }
    }
}
