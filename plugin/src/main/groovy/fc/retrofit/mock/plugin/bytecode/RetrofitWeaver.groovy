package fc.retrofit.mock.plugin.bytecode

import com.quinn.hunter.transform.asm.BaseWeaver
import fc.retrofit.mock.plugin.RetrofitMockExtension
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class RetrofitWeaver extends BaseWeaver {
    private RetrofitMockExtension retrofitMockExtension

    @Override
    void setExtension(Object extension) {
        super.setExtension(extension)
        retrofitMockExtension = (RetrofitMockExtension) extension
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new RetrofitClassVisitor(classWriter, retrofitMockExtension)
    }
}
