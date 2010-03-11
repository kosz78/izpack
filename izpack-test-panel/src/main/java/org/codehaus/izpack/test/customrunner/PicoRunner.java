package org.codehaus.izpack.test.customrunner;

import com.izforge.izpack.installer.container.IInstallerContainer;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.util.List;

/**
 * Custom runner for getting dependencies injected in test with PicoContainer
 *
 * @author Anthonin Bonnefoy
 */
public class PicoRunner extends BlockJUnit4ClassRunner
{
    private Class<?> klass;

    public PicoRunner(Class<?> klass)
            throws InitializationError
    {
        super(klass);
        this.klass = klass;
    }

    @Override
    protected void validateConstructor(List<Throwable> errors)
    {
    }

    @Override
    protected Object createTest() throws Exception
    {
        Class<? extends IInstallerContainer> containerClass = getTestClass().getJavaClass().getAnnotation(Container.class).value();
        IInstallerContainer installerContainer = containerClass.newInstance();
        installerContainer.initBindings();
        installerContainer.addComponent(klass);
        Object component = installerContainer.getComponent(klass);
        return component;
    }
}
