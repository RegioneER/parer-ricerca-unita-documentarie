package it.eng.parer.ricerca.ud;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.*;

public class Profiles {

    public static class Lab implements QuarkusTestProfile {
	@Override
	public Set<String> tags() {
	    return new HashSet<>(Arrays.asList("lab"));
	}
    }

    public static class Core implements QuarkusTestProfile {
	@Override
	public Set<String> tags() {
	    return new HashSet<>(Arrays.asList("unit"));
	}

	@Override
	public String getConfigProfile() {
	    return "test";
	}
    }

    public static class EndToEnd implements QuarkusTestProfile {
	@Override
	public Set<String> tags() {
	    return new HashSet<>(Arrays.asList("e2e"));
	}

	@Override
	public String getConfigProfile() {
	    return "test";
	}
    }
}
