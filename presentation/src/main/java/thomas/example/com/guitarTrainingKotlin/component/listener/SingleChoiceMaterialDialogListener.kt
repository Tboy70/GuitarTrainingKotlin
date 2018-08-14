package thomas.example.com.guitarTrainingKotlin.component.listener

interface SingleChoiceMaterialDialogListener {

    fun onItemSelected(selectedItem: String)

    fun getPositionSelected(which: Int)

    fun onCancelClick()
}