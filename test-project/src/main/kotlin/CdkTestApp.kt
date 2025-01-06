import software.amazon.awscdk.App
import software.amazon.awscdk.Stack
import software.amazon.awscdk.services.s3.Bucket

fun main() {
    val app = App()
    val stack = Stack(app, "testStack")
    val s3Bucket = Bucket(stack, "some-bucket")
    app.synth()
}