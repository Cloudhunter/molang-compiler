package io.github.ocelot.molangcompiler.api;

import io.github.ocelot.molangcompiler.api.bridge.MolangVariable;
import io.github.ocelot.molangcompiler.api.exception.MolangException;
import io.github.ocelot.molangcompiler.core.node.MolangConstantNode;
import io.github.ocelot.molangcompiler.core.node.MolangDynamicNode;
import io.github.ocelot.molangcompiler.core.node.MolangLazyNode;
import io.github.ocelot.molangcompiler.core.node.MolangStaticNode;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * A math expression that can be reduced using a {@link MolangEnvironment}.
 *
 * @author Ocelot
 * @see MolangEnvironment
 * @since 1.0.0
 */
public interface MolangExpression {

    MolangExpression ZERO = of(0);

    /**
     * Resolves the float value of this expression.
     *
     * @param environment The environment to execute in
     * @return The resulting value
     * @throws MolangException If any error occurs when resolving the value
     */
    @ApiStatus.OverrideOnly
    float get(MolangEnvironment environment) throws MolangException;

    /**
     * Resolves the float value of this expression.
     *
     * @param environment The environment to execute in
     * @return The resulting value
     * @throws MolangException If any error occurs when resolving the value
     */
    default float resolve(MolangEnvironment environment) throws MolangException {
        return environment.resolve(this);
    }

    /**
     * Resolves the float value of this expression. Catches any exception thrown and returns <code>0.0</code>.
     *
     * @param environment The environment to execute in
     * @return The resulting value
     */
    default float safeResolve(MolangEnvironment environment) {
        return environment.safeResolve(this);
    }

    /**
     * Creates a {@link MolangExpression} of the specified value.
     *
     * @param value The value to represent as an expression
     * @return A new expression with that value
     */
    static MolangExpression of(float value) {
        return new MolangConstantNode(value);
    }

    /**
     * Creates a {@link MolangExpression} of the specified value.
     *
     * @param value The value to represent as an expression
     * @return A new expression with that value
     */
    static MolangExpression of(boolean value) {
        return new MolangConstantNode(value ? 1.0F : 0.0F);
    }

    /**
     * Creates a {@link MolangExpression} of the specified value that will be computed after every call.
     *
     * @param value The value to represent as an expression
     * @return A new expression with that value
     */
    static MolangExpression of(Supplier<Float> value) {
        return new MolangDynamicNode(value);
    }

    /**
     * Creates a {@link MolangExpression} of the specified value that will be computed once and cached after.
     *
     * @param value The value to represent as an expression
     * @return A new expression with that value
     */
    static MolangExpression lazy(Supplier<Float> value) {
        return new MolangLazyNode(value);
    }

    /**
     * Creates a {@link MolangExpression} of the specified value that will be computed after every call.
     *
     * @param value The value to represent as an expression
     * @return A new expression with that value
     */
    static MolangExpression of(BooleanSupplier value) {
        return new MolangDynamicNode(() -> value.getAsBoolean() ? 1.0F : 0.0F);
    }

    /**
     * Creates a {@link MolangExpression} of the specified value that will be computed once and cached after.
     *
     * @param value The value to represent as an expression
     * @return A new expression with that value
     */
    static MolangExpression lazy(BooleanSupplier value) {
        return new MolangLazyNode(() -> value.getAsBoolean() ? 1.0F : 0.0F);
    }

    /**
     * Creates a {@link MolangExpression} that access a Java variable.
     *
     * @param value The value to represent as an expression
     * @return A new expression with that value
     * @since 2.0.0
     */
    static MolangExpression of(MolangVariable value) {
        return new MolangStaticNode(value);
    }
}
