var UrlSettingsDialog = function(transitioner, deleteItemCB){
    this.transitioner = transitioner;
    this.deleteItemCB = deleteItemCB;
    this.urlSettingsDialog = $('#urlSettingsDialog');
    this.urlSettingsDeleteUrl = $('#deleteUrl');
    this.urlSettingsDeleteUrl.onAndroidTap(this.deleteCurrentUrlItem.bind(this));
    this.urlSettingsCancel = $('#cancelUrlSettings');
    this.urlSettingsCancel.onAndroidTap(this.hideUrlSettingsDialog.bind(this));
}

UrlSettingsDialog.prototype = {
    showUrlSettingsDialog: function(urlItem){
        this.transitioner.downDialog(this.urlSettingsDialog);
        this.currentUrlItem = urlItem;
    },
    hideUrlSettingsDialog: function(cb){
        this.transitioner.upDialog(this.urlSettingsDialog, cb);
    },
    deleteCurrentUrlItem: function(){
        this.hideUrlSettingsDialog(function(){
            this.deleteItemCB(this.currentUrlItem);
        }.bind(this));
    },
}
