package thomas.example.com.guitarTrainingKotlin.component.listener

interface MaterialDialogComponent {
    fun showProgressDialog(title: String, content: String, color: Int)
    fun showSingleChoiceDialog(
        title: String,
        items: List<String>,
        selectedItem: String?,
        color: Int,
        cancelable: Boolean,
        singleChoiceMaterialDialogListener: SingleChoiceMaterialDialogListener
    )

    fun showMultiChoiceDialog(
        title: String,
        content: String,
        color: Int,
        multipleChoiceMaterialDialogListener: MultipleChoiceMaterialDialogListener
    )

    fun dismissDialog()
}