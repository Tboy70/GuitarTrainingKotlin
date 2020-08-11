package thomas.guitartrainingkotlin.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import thomas.guitartrainingkotlin.presentation.component.*
import thomas.guitartrainingkotlin.presentation.component.listener.*

@Module
@InstallIn(ActivityComponent::class)
abstract class ComponentModule {

    @Binds
    @ActivityScoped
    abstract fun bindDialogComponent(dialogComponentImpl: DialogComponentImpl): DialogComponent

    @Binds
    @ActivityScoped
    abstract fun bindDurationComponent(durationComponentImpl: DurationComponentImpl): DurationComponent

    @Binds
    @ActivityScoped
    abstract fun bindErrorRendererComponent(errorRendererComponentImpl: ErrorRendererComponentImpl): ErrorRendererComponent

    @Binds
    @ActivityScoped
    abstract fun bindExerciseUIComponent(exerciseUIComponentImpl: ExercisesUIComponentImpl): ExercisesUIComponent

    @Binds
    @ActivityScoped
    abstract fun bindSnackbarComponent(snackbarComponentImpl: SnackbarComponentImpl): SnackbarComponent
}