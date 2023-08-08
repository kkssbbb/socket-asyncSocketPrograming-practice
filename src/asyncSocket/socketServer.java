package asyncSocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;

public class socketServer {

    //AsynchronousChannelGroup
    private static AsynchronousChannelGroup asyncChannelGroup;
    private static AsynchronousServerSocketChannel asyncServerSocketChannel;

    public static void main(String[] args) throws IOException {


        System.out.println("Server Start");

        try {
            //비동기 채널 그룹 생성
            asyncChannelGroup = AsynchronousChannelGroup.withFixedThreadPool(10, Executors.defaultThreadFactory());
            //비동기 서버 소켓 채널 생성
            asyncServerSocketChannel = AsynchronousServerSocketChannel.open(asyncChannelGroup);
            // asyncSocketChannel을 사용하는 작업을 수행

            asyncServerSocketChannel.bind(new InetSocketAddress(55555));

            //클라이언트 연결 수락
            asyncServerSocketChannel.accept(
                    null,
                    new CompletionHandler<AsynchronousSocketChannel, Void>() {
                        @Override
                        public void completed(AsynchronousSocketChannel asc,
                                              Void attachment) {

                            //클라이언트가 보낸 데이터 받기
                            receive(asc);

                            //다음 클라이언트 연결 수락하기
                            asyncServerSocketChannel.accept(null, this);
                        }

                        @Override
                        public void failed(Throwable exc, Void attachment) {
                        }
                    }
            );

            try {
                System.in.read();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            asyncServerSocketChannel.close();
            asyncChannelGroup.shutdown();
        }
        System.out.println("Server Shutdown");
    }


    //클라이언트가 보낸 데이터 받기
    public static void receive(AsynchronousSocketChannel asynchronousSocketChannel) {


    }

    //클라이언트로 데이터 보내기
    public static void send(AsynchronousSocketChannel asynchronousSocketChannel) {

    }


}


