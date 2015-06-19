package akka.tutorial.first.java;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;
import akka.util.Duration;

import java.util.concurrent.TimeUnit;


/**
 * Hello Akka!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello Akka!");
    }


    static class Calculate {

    }

    static class Work {
        private final int start;
        private final int nrOfElements;

        Work(int start, int nrOfElements) {
            this.start = start;
            this.nrOfElements = nrOfElements;
        }

        public int getStart() {
            return start;
        }

        public int getNrOfElements() {
            return nrOfElements;
        }
    }

    static class Result {
        private final double value;

        Result(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    static class PiApproximation {
        private final double pi;
        private final Duration duration;


        PiApproximation(double pi, Duration duration) {
            this.pi = pi;
            this.duration = duration;
        }

        public double getPi() {
            return pi;
        }

        public Duration getDuration() {
            return duration;
        }
    }

    public static class Worker extends UntypedActor {

        @Override
        public void onReceive(Object message) throws Exception {
            if (message instanceof Work) {
                Work work = (Work) message;
                double result = calculatePiFor(work.getStart(), work.getNrOfElements());
                getSender().tell(new Result(result), getSelf());
            } else {
                unhandled(message);
            }


        }

        private double calculatePiFor(int start, int nrOfElements) {
            double acc = 0.0;
            for (int i = start * nrOfElements; i <= ((start + 1) * nrOfElements - 1); i++) {
                acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1);
            }
            return 0;
        }

    }

    public static class Master extends UntypedActor {

        private final int nrOfMessages;
        private final int nrOfElements;
        private final ActorRef listener;
        private final ActorRef workerRouter;

        private double pi;
        private int nrOfResults;
        private final long start = System.currentTimeMillis();


        public Master(final int nrOfWorkers, int nrOfMessages, int nrOfElements, ActorRef listener) {
            this.nrOfMessages = nrOfMessages;
            this.nrOfElements = nrOfElements;
            this.listener = listener;
            this.workerRouter = this.getContext().actorOf(new Props(Worker.class)
                    .withRouter(new RoundRobinRouter(nrOfWorkers)), "workerRouter");

        }

        @Override
        public void onReceive(Object message) {
            // handle message
            if (message instanceof Calculate) {
                for (int start = 0; start < nrOfMessages; start++) {
                    workerRouter.tell(new Work(start, nrOfElements), getSelf());
                }
            } else if (message instanceof Result) {
                Result result = (Result) message;
                pi += result.getValue();
                nrOfResults += 1;
                if (nrOfResults == nrOfMessages) {
                    // send the result to listner
                    Duration duration = Duration.create(System.currentTimeMillis() - start,
                            TimeUnit.MILLISECONDS);
                    listener.tell(new PiApproximation(pi, duration), getSelf());
                    // stops this actor and all its supervised childeren
                    getContext().stop(getSelf());

                } else {
                    unhandled(message);
                }
            }

        }
    }


}



