package github.veikkoroc.test_codec;

import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.remote.transport.netty.codec.KryoEncoder;
import github.veikkoroc.serialize.KryoSerializer;
import github.veikkoroc.serialize.Serializer;
import org.junit.Test;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 10:35
 */
public class Main extends KryoEncoder{
    public Main(Serializer serializer, Class<?> genericClass) {
        super(serializer, genericClass);
    }

}
