package fc.retrofit.mock.plugin

import com.android.build.api.transform.Context
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.quinn.hunter.transform.HunterTransform
import com.quinn.hunter.transform.RunVariant
import fc.retrofit.mock.plugin.bytecode.RetrofitWeaver
import org.gradle.api.Project

class RetrofitMockTransform extends HunterTransform {

    private Project project
    private RetrofitMockExtension retrofitMockExtension;

    RetrofitMockTransform(Project project) {
        super(project)
        this.project = project
        project.extensions.create("retrofitMock", RetrofitMockExtension)
        this.bytecodeWeaver = new RetrofitWeaver()
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        retrofitMockExtension = project.getExtensions().getByName("retrofitMock")
        if (retrofitMockExtension == null) {
            retrofitMockExtension = new RetrofitMockExtension()
        }
        bytecodeWeaver.setExtension(retrofitMockExtension)
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)
    }

    @Override
    protected RunVariant getRunVariant() {
        if (retrofitMockExtension == null || retrofitMockExtension.onlyDebug) {
            return RunVariant.DEBUG
        } else {
            return super.getRunVariant()
        }
    }

    @Override
    boolean isIncremental() {
        return false
    }
}
