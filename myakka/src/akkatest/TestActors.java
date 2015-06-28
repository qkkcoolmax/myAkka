package akkatest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.testkit.TestActor;

public class TestActors extends UntypedActor {

	private int maxPPcount = 5;
	private int count = 0;

	public TestActors(int count) {
		this.maxPPcount = count;
	}

	public TestActors() {

	}

	static enum msg {
		PING, PONG;
	}

	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("totalcount" + maxPPcount);
		ActorRef pr = getContext().actorOf(
				Props.create(PongActors.class, "pongpong"), "pongActor");
		pr.tell(msg.PING, getSelf());

	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		if (arg0 == msg.PONG) {
			System.out.println("get pong");
			count++;
		}
		if (count == maxPPcount) {
			getContext().system().shutdown();
		} else {
			getSender().tell(msg.PING, getSelf());
		}
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("MySystem");
		ActorRef a = system.actorOf(Props.create(TestActors.class, 10), "ping");

	}
}

class PongActors extends UntypedActor {

	private String actorname;

	public PongActors(String name) {
		this.actorname = name;
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		if (arg0 == TestActors.msg.PING) {
			System.out.println("got ping");
			getSender().tell(TestActors.msg.PONG, getSelf());
		}

	}
}