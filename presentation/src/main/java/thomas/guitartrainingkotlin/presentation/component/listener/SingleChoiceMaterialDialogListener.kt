package thomas.guitartrainingkotlin.presentation.component.listener

interface SingleChoiceMaterialDialogListener {

    fun onItemSelected(selectedItem: String)

    fun getPositionSelected(which: Int)

    fun onCancelClick()
}