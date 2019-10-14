import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbJSON;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXML;
import com.ibm.broker.plugin.MbXMLNSC;


public class JCN_DYNAMIC_XML_JavaCompute extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage();
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			// ----------------------------------------------------------
			// Add user code below
			MbElement firstRoot = inMessage.getRootElement().getFirstElementByPath("XMLNSC/DETAILS/FIRSTNAME");
			String str1 = firstRoot.getValueAsString();
			MbElement secondRoot = inMessage.getRootElement().getFirstElementByPath("XMLNSC/DETAILS/LASTNAME");
			String str2 = secondRoot.getValueAsString();
			MbElement thirdRoot = inMessage.getRootElement().getFirstElementByPath("XMLNSC/DETAILS/MOBILE");
			String str3 = thirdRoot.getValueAsString();
			
			
			MbElement outRoot = outMessage.getRootElement();
						
			MbElement outParser1 = outRoot.createElementAsLastChild(MbXMLNSC.PARSER_NAME);
			MbElement outMsg = outParser1.createElementAsLastChild(MbXMLNSC.FOLDER, "ACCOUNTINFO", null);
			outMsg.createElementAsLastChild(MbXMLNSC.FIELD, "NAME", str1);
			outMsg.createElementAsLastChild(MbXMLNSC.FIELD, "SURNAME", str2);
			outMsg.createElementAsLastChild(MbXMLNSC.FIELD, "PHONE", str3);
			
			
			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}

}
