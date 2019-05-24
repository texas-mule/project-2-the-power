# Project 2 The Power - Stock Application

## Main Project Responsibilites

Jenkins:
Sean Gordon

Test cases:
Raj Mahal
Sean Gordon

Three SpringBoot API’s
- Stock Info
- User Info
- News Articles

Stock Info SpringBoot API
- Dylan Troutman
- Sean Gordon

User Info SpringBoot API
- Mohamad Khalifa
- Raj Mahal

News Article SpringBoot API
- Hayden Huffines 
- Andres Orellana

# Project API's Minimal Viable Product

## User service
- buy stock
- add funds
- add shares
- remove shares
- add custom index
- credits
- ranking system


## Articles
- raw text
- sources
- have already seen article
- custom search filters
- when the user last saw the article and when it was written
- join table with company mentions


## Stock Info
- realtime comaprisons between stocks
- company names ticker names for company names
- custom index and generate a report over a timeframe
- create a new index
- associating a fair price to a stock
- economic barameter

------------------------------------------------------------------------------------------------------------------

# API Ports

## Gameification
### Port: 7070

## Stock Info
### Port: 9090

## Article Parser
### Port: 5050

------------------------------------------------------------------------------------------------------------------

# Project Sprints

## Sprint 1
5/22/2019

- Meet up to pick a project
- split up into groups of two
- pick which part of the project each person would like to work on


## Sprint 2
5/23/2019

- have git repository done
- get git setup for all developers
- talk about git standards to commit
- setting up databases, each api can have their own type of database
- get Main project ideas cleared out and get the 3 separate projects cleared out
- clear out Mule endpoints

## Sprint 3
5/24/2019

- have all requirements ready for major code sprint
- have jenkins setup and ready
- all API’s make first commit

## Sprint 4
5/27/2019

- all API’s have 80% of the code ready
- 12 test cases built, 4 test cases per API 
- work out mule endpoints for user experience
- start working on RAML documentation

## Sprint 5
5/28/2019

- 24 test cases built
- all projects should be building correctly on Jenkings with minimum 90% of each API done
- have RAML documentation done

## Sprint 6
5/29/2019

- Code review, only cleaning up code commits such as deleting sysout lines adding comments etc. All functionality code is 100% done at this point.

## Sprint 7
5/30/2019

- work on presentation
- delegate the parts of the presentation
- work on slides
- practice the presentation several times

------------------------------------------------------------------------------------------------------------------

# Project Coding Expectations/Standards

## These are important so that we are all on the same page and we will be able to provide a workable end result.

- all code should be done by 5/29/2019 no exceptions. The project should have all functionality done and should be a full working and done project

- prevent from steping on each others code

- focus on your own project

- focus on getting the project done rather than adding features that are pluses for the project

- commiting source code to git should be done daily

------------------------------------------------------------------------------------------------------------------

# Git Command Line Instructions
Before commiting you will need pull the more recent changes type
```
git pull
```

First use this command to create a new branch to the features your going to push to
```
git checkout -b newbranch
```

Then use this command for every file you changed
```
git add 'file that was changed'
```

Then commit your changes and write a message that explains what you are commiting
```
git commit -m “message”
```

The last thing is to your changes to the branch repository, merging to master will be taken care of by the git master or Jenkins
```
git push origin newbranch
```

# Git Eclipse Instructions
Before commiting you will need pull the more recent changes type
-On the git Repositories tab right click your project name and select pull

First follow these steps to create a new branch to the features your going to push to
- Click Git icon on the upper right then under the git repositories tab right click the project name and select switch to New Branch
- Under unstaged changes tab drag and drop the files from unstaged to staged put the files you have made changes to. 
- If you don't see any files that you changed on the unstaged files then on the upper right on top of the commit tab click the refresh button and this
will refresh the view.
- Write a message under the commit tab of the changes you made and then click the Commit and Push button.


