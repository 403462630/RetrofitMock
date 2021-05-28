package fc.retrofit.mock.plugin.bytecode

import fc.retrofit.mock.plugin.RetrofitMockExtension
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class RetrofitClassVisitor extends ClassVisitor {
    private String className
    private RetrofitMockExtension retrofitMockExtension
    RetrofitClassVisitor(ClassVisitor classVisitor, RetrofitMockExtension retrofitMockExtension) {
        super(Opcodes.ASM7, classVisitor)
        this.retrofitMockExtension = retrofitMockExtension
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (className == "retrofit2/ServiceMethod") {
            return new RetrofitMethodVisitor(retrofitMockExtension, mv, access, name, descriptor)
        } else  {
            return mv
        }
    }
}
