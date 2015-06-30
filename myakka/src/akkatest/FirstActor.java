package akkatest;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class FirstActor extends UntypedActor {

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			System.out.println("i got:" + (String) message);
			System.out.println("now i run by thread" + Thread.currentThread().getName()
					+ "take ");

		}
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("mySystem");
		
		ActorRef actor1 = system.actorOf(Props.create(FirstActor.class),
				"actor");
		//actorOf 创建actor actorFor获取已经存在的actor
		actor1.tell("hahahah", ActorRef.noSender());
		system.shutdown();

	}

}
