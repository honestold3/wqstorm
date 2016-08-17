package storm.drpc;

import backtype.storm.utils.DRPCClient;

public class DrpcRemoteClient {

	public static void main(String[] args) throws Exception {
		DRPCClient client = new DRPCClient("honest", 3772);
		String result = client.execute("hello", "world!!!!!");
		System.out.println(result);
	}

}
