package in.jetbra.plugins.mymap;

import com.janetfilter.core.models.FilterRule;
import com.janetfilter.core.plugin.MyTransformer;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import jdk.internal.org.objectweb.asm.tree.VarInsnNode;

import java.util.List;

public class MapTransformer implements MyTransformer {
    private final List<FilterRule> rules;

    public MapTransformer(List<FilterRule> rules) {
        this.rules = rules;
    }

    @Override
    public String getHookClassName() {
        return "com/google/gson/internal/LinkedTreeMap";
    }

    @Override
    public byte[] transform(String className, byte[] classBytes, int order) throws Exception {
        PutFilter.setRules(this.rules);
        ClassReader reader = new ClassReader(classBytes);
        ClassNode node = new ClassNode(327680);
        reader.accept(node, 0);

        for (MethodNode mn : node.methods) {
            if ("put".equals(mn.name) && "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;".equals(mn.desc)) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(25, 1));
                list.add(new VarInsnNode(25, 2));
                list.add(new MethodInsnNode(184, "in/jetbra/plugins/mymap/PutFilter", "testPut", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false));
                list.add(new VarInsnNode(58, 2));
                mn.instructions.insert(list);
            }
        }

        ClassWriter writer = new ClassWriter(3);
        node.accept(writer);
        return writer.toByteArray();
    }
}
