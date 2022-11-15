import org.aspectj.weaver.AnnotationTargetKind.ANNOTATION_TYPE
import org.aspectj.weaver.AnnotationTargetKind.TYPE
import java.lang.annotation.Documented
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass



//@Target([TYPE, ANNOTATION_TYPE])
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = arrayOf(PasswordMatchesValidator::class))
@Documented
annotation class PasswordMatches(
    val message: String = "Passwords don't match",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)

class PasswordMatchesValidator : ConstraintValidator<PasswordMatches?, Any> {
    override fun initialize(constraintAnnotation: PasswordMatches?) {}
    override fun isValid(obj: Any, context: ConstraintValidatorContext): Boolean {
        val user = obj as UserDto
        return user.getPassword().equals(user.getMatchingPassword())
    }
}