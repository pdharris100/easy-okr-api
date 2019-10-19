package com.easyokr.deserialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.easyokr.model.Domain;
import com.easyokr.model.KeyResult;
import com.easyokr.model.Objective;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectiveDeserializer extends StdDeserializer<Objective> {

	public ObjectiveDeserializer() {
        this(null);
    }	

	protected ObjectiveDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Objective deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper) parser.getCodec();
		ObjectNode node = parser.getCodec().readTree(parser);
		ObjectNode domainNode = (ObjectNode) node.get("domain");
		Domain domain = mapper.treeToValue(domainNode, Domain.class);
		IntNode idNode = (IntNode) node.get("id");
		long id = 0;
		if (idNode != null) {
			id = (Integer) idNode.numberValue();
		}
        String description = node.get("description").asText();
        Objective objective = new Objective(id, domain, description);
        ArrayNode keyResultsNode = (ArrayNode) node.get("keyResults");
		Iterator<JsonNode> iter = keyResultsNode.iterator();
		while (iter.hasNext()) {
			ObjectNode objNode = (ObjectNode)iter.next();
			KeyResult keyResult = mapper.treeToValue(objNode, KeyResult.class);
			keyResult.setObjective(objective);
			objective.addKeyResult(keyResult);
		}
		return objective;
	}

}
