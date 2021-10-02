package io.github.ocelot.molangcompiler.core.node;

import io.github.ocelot.molangcompiler.api.MolangEnvironment;
import io.github.ocelot.molangcompiler.api.MolangExpression;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

/**
 * @author Ocelot
 */
@ApiStatus.Internal
public class MolangStaticNode implements MolangExpression
{
    private final Supplier<Float> value;

    public MolangStaticNode(Supplier<Float> value)
    {
        this.value = value;
    }

    @Override
    public float resolve(MolangEnvironment environment)
    {
        return this.value.get();
    }

    @Override
    public String toString()
    {
        return Float.toString(this.value.get());
    }
}
