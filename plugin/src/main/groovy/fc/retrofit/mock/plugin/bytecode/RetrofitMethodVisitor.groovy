package fc.retrofit.mock.plugin.bytecode

import fc.retrofit.mock.plugin.RetrofitMockExtension
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class RetrofitMethodVisitor extends AdviceAdapter {

    private RetrofitMockExtension retrofitMockExtension
    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param api the ASM API version implemented by this visitor. Must be one of {@link
     *     Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access the method's access flags (see {@link Opcodes}).
     * @param name the method's name.
     * @param descriptor the method's descriptor (see {@link Type Type}).
     */
    protected RetrofitMethodVisitor(RetrofitMockExtension retrofitMockExtension, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM7, methodVisitor, access, name, descriptor)
        this.retrofitMockExtension = retrofitMockExtension
    }

    @Override
    void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
        if (name == "parseAnnotations") {
            mv.visitVarInsn(ALOAD, 0)
            mv.visitVarInsn(ALOAD, 1)
            mv.visitTypeInsn(NEW, "fc/retrofit/mock/library/RetrofitData")
            mv.visitInsn(DUP)
            mv.visitLdcInsn(retrofitMockExtension.baseUrl == null ? "" : retrofitMockExtension.baseUrl)
            mv.visitLdcInsn(retrofitMockExtension.xApiKey == null ? "" : retrofitMockExtension.xApiKey)
            mv.visitMethodInsn(INVOKESPECIAL, "fc/retrofit/mock/library/RetrofitData", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", false)
            mv.visitMethodInsn(INVOKESTATIC, "fc/retrofit/mock/library/MockRetrofitHelper", "wrapRetrofit", "(Lretrofit2/Retrofit;Ljava/lang/reflect/Method;Lfc/retrofit/mock/library/RetrofitData;)Lretrofit2/Retrofit;", false)
            mv.visitVarInsn(ASTORE, 0)
        }
    }
}
