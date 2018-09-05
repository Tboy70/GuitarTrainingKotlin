package thomas.example.com.guitarTrainingKotlin.ui.objectwrapper

import thomas.example.com.model.Program
import java.io.Serializable

class ProgramObjectWrapper(@Transient val program: Program) : Serializable