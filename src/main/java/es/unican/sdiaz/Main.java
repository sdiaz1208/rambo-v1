package es.unican.sdiaz;

import java.io.IOException;

import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestFileDetail;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

public class Main {

    public static void main(String[] args) throws IOException {

        // Inputs from GitHub Action
        String token = System.getenv("GITHUB_TOKEN");
        String repoName = System.getenv("GITHUB_REPOSITORY"); // owner/repo
        int prNumber = Integer.parseInt(args[0]); // PR number passed as argument

        GitHub github = new GitHubBuilder()
                .withOAuthToken(token)
                .build();

        GHRepository repo = github.getRepository(repoName);

        GHPullRequest pr = repo.getPullRequest(prNumber);

        System.out.println("Analyzing PR #" + prNumber);

        boolean modelChangeDetected = false;

        for (GHPullRequestFileDetail file : pr.listFiles()) {

            String filename = file.getFilename();
            System.out.println("Changed file: " + filename);

            if (filename.endsWith(".model") || filename.endsWith(".ecore")) {

                modelChangeDetected = true;

                // Your analysis logic here
                System.out.println("Model change detected in: " + filename);

                // Example placeholder logic
                runModelAnalysis(filename);
            }
        }

        String comment;

        if (modelChangeDetected) {
            comment = """
                    ## 🤖 Rambo Analysis

                    Model-related changes were detected in this PR.

                    Files analyzed successfully.
                    """;
        } else {
            comment = """
                    ## 🤖 Rambo Analysis

                    No `.model` or `.ecore` changes detected.
                    """;
        }

        pr.comment(comment);

        System.out.println("Comment posted to PR.");
    }

    private static void runModelAnalysis(String file) {
        // Placeholder for your real logic
        System.out.println("Running analysis for " + file);
    }
}
