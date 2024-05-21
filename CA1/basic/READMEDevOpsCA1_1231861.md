# Class Assignment 1

## Introduction

This technical report outlines the development process during the execution of Class Assignment 1 (CA1), which centers
on implementing version control best practices utilizing Git. Additionally, it explores an alternative version control
system for comparative analysis. The assignment comprises two main parts: initially working directly on the master
branch, followed by the incorporation of branching strategies for feature enhancements and bug fixes.

## Part 1: Direct Work on Master Branch

### Objective

To establish the initial configuration and incorporate a new feature directly into the master branch without resorting
to additional branches.

### Implementation Steps

1. Initialize the Git repository
   ```bash
   git init
   ```
    - adds a ".git" directory to the current directory
2. Clone the tutorialReactSpringRest folder into the project's directory:
   ```bash
   git clone git@github.com:spring-guides/tut-react-and-spring-data-rest.git
   ```
3. Add all files to the staging area:
   ```bash
   git add .
   ```
    - Before being prepared for commit and subsequent push to the remote repository, changes need to be staged.

4. Commit the added files:
   ```bash
   git commit -m "Added CA1 with new files"
   ```
5. Push the commit to the remote repository:
   ```bash
   git remote add origin <repository-URL>
   git push origin master
   ```
    - The first step establishes the connection between the local repository to the remote one.
    - The second step uploads local repository content to the remote repository location.

#### Version Tagging

1. Tag the initial version of the application as `v1.1.0` and push the tag to the remote repository:
   ```bash
   git tag v1.1.0 
   git push origin v1.1.0
   ```
    - creates a tag named 'v1.1.0', holding the message stated above (after "-m")
    - pushes (same logic as before) the corresponding tag to the origin of the repository, saving the information about
      the state of the project in the tag itself

#### New Feature - Job Title Field and Tests

1. Creation of Issues
    - In the remote repository, issues should be created for this new feature (named after the portions of the function
      needed to create, e.g "JobYears untested", "Jobyears feature testing", "JobYears debugged")
    - These issues are issued numbers, that will be afterwards used in the way I'll further on explain.

2. After implementing the feature and tests, add the changes and commit:
   ```bash
   git add .
   git commit -m "[Feat] - Added JobTitle field, EmployeeTest class with tests, and updated DatabaseLoader class with an additional field. Close #4"
   ```

The issue is closed automatically by the commit message, as it contains the "Close #4" notation. This is a way to keep
track of the issues and the commits that solve them.

#### New Feature - Job Years Field and Tests

1. The same process was done for the implementation of the JobYears field and its respective tests

 ```bash
   git add .
   git commit -m "[Feat] - Added JobYears field, tests in the EmployeeTest class and updated DatabaseLoader class with an additional field. Close #3"
  ```

2. Tags were created and associated with the previous commit in order to mark the version 1.2.0 that indicates the
   current changes that were made and features that were added and the state of the software that is being developed.
   Following this step , the tag is then pushed to the remote repository:
   ```bash
   git tag v1.2.0 -m 
   git push   
   git commit -m "[Feat] -Updated .gitignore file. Close #6"
   git tag ca1-part1
   git push origin ca1-part1
   ```
    - Note: In my case the second tag ca1-part1 was associated with the commit: "[Feat] -Updated .gitignore file. Close
      #6"  because it wasn't present in the repository and it was the final step of this part.
    - This concludes the part 1 of the Class Assignment

## Part 2: Using Branches for Development

### Objective

Initially, our project begins with a main/master branch, serving as the foundational and stable version. It's essential
to utilize supplementary branches for both development and bug fixing purposes.
This practice is crucial for averting project disruptions, ensuring that merges between branches occur smoothly without
compromising the integrity of the existing codebase.

### Implementation

Once more, we'll create issue/implementation related documentation within the remote repository. We'll delineate the
issue, its associated problem, and the planned implementation.
Furthermore, we'll link these issues to the corresponding branch we're actively developing on. This practice ensures a
more coordinated collaboration and facilitates future repository merges.

1. Next, we'll create and switch to the new email-field branch. Using the checkout command with the -b option, we'll
   both create and move to this branch if it doesn't already exist:
   ```bash
   git checkout -b email-field
   ```
2. Continuing in a similar logic to our previous actions, we'll implement the feature (along with corresponding tests),
   and then stage, commit, and push the changes to the relevant branch (email-field):

  ```bash
   git add .
   git commit -m "[Feat] - Added email field, updated respective tests in EmployeeTest, and updated DatabaseLoader class with the additional field. Close #7."
   git push origin email-field
   ```

3. Following the completion of the feature implementation, we'll merge this branch into the main branch. Upon ensuring
   proper execution and addressing any conflicts, we'll have an updated version ready to go.

```bash
   git checkout master
   git merge --no-ff email-field
   git tag v1.3.0
   git push origin master
   git push origin v1.3.0
   ```

#### Bug Fix - Email Validation

1. To address the identified issue, we'll initiate the creation of an issue specifically related to fixing invalid email
   validation. This issue will guide our subsequent actions in implementing the necessary changes.
   The system should only accept Employees with valid email (containing "@")".
2.
3. Firstly, as we've done with the previous feature, we need to create and switch to the appropriate branch:
   ```bash
   git checkout -b fix-invalid-email
   ```
3. After implementing the changes, adding the tests, we will once again stage, commit, and push the changes:

```bash
   git add .
   git commit -m "[Fix] -Creation of the branch fix-invalid-email. Only emails that contain the @sign and arent empty or null are accepted. Added validation tests. Close #8"
   git push origin fix-invalid-email
   ```

4. With the bug fix implemented, we'll merge the changes into the main branch and assign the corresponding tag:
   ```bash
   git checkout master
   git merge --no-ff fix-invalid-email
   git tag v1.3.1
   git push origin master
   git push origin v1.3.1
   ```
5. Document the completion of part 2 of the assignment:
   ```bash
   git tag ca1-part2
   git push origin ca1-part2
   ```

### Mercurial SCM as an alternative to Git

Despite a decrease in its popularity since its inception in 2005, Mercurial is still employed by prominent organizations
such as Facebook and Mozilla.
The major difference between Git and Mercurial lies in their branching structures.

Mercurial, similarly to Git, functions as a distributed version control system (DVCS), allowing developers to monitor
and
oversee changes to their codebase. Nonetheless, there are notable disparities and resemblances between the two,
impacting their suitability based on project requirements and team preferences.

In terms of performance, Git typically outshines Mercurial for large-scale projects, owing to its adept management of
branches and compact data format. However, Mercurial offers satisfactory performance for most endeavors and may present
a simpler interface for fundamental tasks.

While Mercurial may boast a simpler user experience, its branching model can be hard to manage at times.
In contrast to Git's flexibility in creating, deleting, and switching branches at will, Mercurial's branching structure
is more intricate, due to the fact that the branches are permanent and cannot be deleted, potentially leading to
repository clutter.
It is crucial to be cautious in order to prevent inadvertently committing changes to the wrong branch.

In sum, Git is more widely adopted, resulting in a more extensive array of tools and integrations, encompassing popular
platforms such as GitHub and Bitbucket. Even though Mercurial is supported by numerous tools, its
ecosystem remains comparatively smaller.
Utilizing Mercurial for Objectives
To achieve the assignment's goals with Mercurial:

#### Setting up a repository in Mercurial

To set up a repository in Mercurial, follow these steps:

Install Mercurial: Get Mercurial by downloading and installing it from the Mercurial
website https://www.mercurial-scm.org/downloads

Navigate to Directory: Open a terminal and go to the directory where the repository will reside:

```bash
cd path/to/directory
```

3. Execute the following command to initialize a new repository:

```bash
hg init
```

4. Add the files to the repository:

```bash
hg add .
```

5. Commit the files that were previously added to the repository:

```bash
hg commit -m "Initial commit"
```

6. Push the repository to a remote location:

```bash
hg push <repository-URL>
```

7. Assign a tag to the commit for version identification:

```bash
hg tag -m "Initial commit using mercurial" v1.0.0
```

To fulfill the assignment's tasks, replace the Git commands with the corresponding Mercurial commands:

1. Branch Creation:

```bash
hg branch email-field
hg commit -m "Added email field"
```

2. Switch to the new Branch:

```bash
hg update email-field
```

3. Merge the email-field branch into the main branch:

```bash
hg merge email-field
hg commit -m "Merged email-field branch"
```

4. Just like previously done when using Git, the creation of the final tag would be as follows:

```bash
    hg tag ca1-part2
    hg push --tags
```

## Other Mercurial Commands

Essential Mercurial Commands
hg clone: Clones a repository.
hg config: Configures the user's settings.
hg diff: Views differences in files.
hg help: provides access to the built-in help system
hg forget: Removes files from the Version Control System (VCS).
hg log: Lists repository history.
hg pull: Retrieves changes from a remote repository.
hg revert: Reverts changes in the working directory.
hg rollback: Reverts the last commit made.
hg status: Shows working directory status.
hg summary: Provides a summary of the repository.
hg update: Updates the working directory to a specific revision.

Repository URL: https://github.com/tiagopereiraswitch/devops-23-24-JPE-1231861

## Conclusion

In summary, the CA1 assignment's goals were effectively met through the use of Git, which involved the integration
of branching techniques along with the addition of new functionalities and resolution of bugs. In the context of seeking
an alternative version control system, Mercurial emerged as a viable candidate, offering a simpler interface and
streamlined user experience. However, Git's widespread adoption, extensive tooling, and robust branching model
position it as the preferred choice for most modern software development projects.
