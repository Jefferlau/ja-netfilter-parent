package in.jetbra.plugins.mymap;

import com.janetfilter.core.Environment;
import com.janetfilter.core.models.FilterRule;
import com.janetfilter.core.plugin.MyTransformer;
import com.janetfilter.core.plugin.PluginEntry;

import java.util.ArrayList;
import java.util.List;

public class MyPluginEntry implements PluginEntry {
    private final List<MyTransformer> transformers = new ArrayList();

    public MyPluginEntry() {
    }

    public void init(Environment environment, List<FilterRule> filterRules) {
        this.transformers.add(new MapTransformer(filterRules));
    }

    @Override
    public String getName() {
        return "MyMap";
    }

    @Override
    public String getAuthor() {
        return "jetbra.in";
    }

    @Override
    public String getVersion() {
        return "v1.0.1";
    }

    @Override
    public String getDescription() {
        return "MyMap plugin for ja-netfilter";
    }

    @Override
    public List<MyTransformer> getTransformers() {
        return this.transformers;
    }
}
