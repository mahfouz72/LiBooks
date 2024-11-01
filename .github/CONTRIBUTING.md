# Workflow and Contribution Guidelines

This document outlines the workflow and contribution guidelines to ensure a smooth,
consistent, and high-quality process for development.

## 1. Branching Strategy

### 1.1 Main Branch
- **Direct Commits**: The main branch is never committed directly.
- **Milestone Merges**: Only milestone branches are merged into `main` once they are completed and reviewed.

### 1.2 Milestone Branches
- **For each milestone**, we check out a branch from main with name milestone_x
  where x is the milestone number

### 1.3 Feature Branches
- **Creating a Feature Branch**: For each feature within a milestone, check out a branch from the milestone branch
  with name milestone_x/feat-[JIRA_ID]-[DESCRIPTION]
    - **JIRA_ID** is the ID of the Jira story for this feature
    - **DESCRIPTION** is a brief 3-5 words description of the feature
    - **Example**: `git checkout -b milestone_1/feat-1234-add-login-page`


After we are done with the feature branch and unit tests pass, we merge it to the
milestone branch.

After we are done with the milestone branch, which is at the end of the sprint and
before delivering the code in the discussions, we merge it to the main branch.

---

## 2. Version Control Process

This guide outlines the specific Git commands and workflow practices
to manage feature branches and milestone branches.

### 2.1 Creating Milestone Branches

```
git checkout main
git pull origin main
git checkout -b milestone-1
```

### 2.2 Creating Feature Branch

```
git checkout milestone-1
git checkout -b milestone_1/feat-1234-add-login-page
```

### 2.3  Commit Changes to the Feature Branch

```
git add .  
git commit -m "commit message"
git push origin milestone_1/feat-1234-add-login-page
```

### 2.4 Repeating Commits and Squashing

For subsequent commits, follow step 2.3 until development on the feature is complete. 
Before merging to the milestone branch, make sure your branch is up-to-date
with the milestone branch and squash all additional commits into a single commit to keep the history clean:

1. Pull Latest Milestone Branch Changes:
    ```
    git checkout milestone-1
    git pull origin milestone-1
    git checkout milestone_1/feat-1234-add-login-page
    git rebase milestone-1
    ```
2. Squash Commits Before Push:
    ```
    git rebase -i milestone-1  # squash extra commits into one
    git push --force origin milestone_1/feat-1234-add-login-page
    ```
   
If conflicts arise, resolve them,
then stage changes using `git add .`
and continue with `git rebase --continue`.

### 2.5 Commit Message Guideline
- Keep it concise and descriptive
- Use the imperative mood: Example: "Add user authentication" (not "Added" or "Adds").
- Use the following prefixes in the commit message:
1. **[JIRA_ID]**: Description for new features
   - Example: "feat-1234: Add login page functionality"
2. **[fix]**: Description for bug fixes:
   - Example: "fix: login endpoint should return 403 on invalid credentials"
3. **[config]**: Description for configuration changes:
   - Example: "config: Add LineLengthCheck in checkstyle.xml"
4. **[refactor]**: Description for refactoring code
    - Example: "refactor: Update login service logic"
5. **[issue #issueNumber]**: Description for commits related to a GitHub issue
    - Example : "issue #7: Improve validation messages"
6. **[doc]** for documentation updates

---

## 3. Pull Request And Code Review
- **Approval Requirements**: At least **two** approvals are required for each PR
- **Merge Method**: Always use Squash and Merge to keep the commit history clean.
- **CI Status**: All CI checks **must** pass before merging the PR
- **Review criteria**:
  - **Adhering to a coding standard and conventions**: This is automated by checkstyle GitHub workflow
  - **Code clarity and readability**: Focus on any improvements that makes the code more readable
  - **No redundant code**: logging/debugging statements like `println`, redundant comments, commented code, etc.
  - **Testing**
- **PR Title**: Same as commit message