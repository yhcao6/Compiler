# Store the files generated by JavaCC
JAVACC_OUTDIR = myparser
JAVACC_FILE = Asgn1.jj
PARSER = $(JAVACC_OUTDIR)/Asgn1.java

all: Asgn1.class

Asgn1.class: Asgn1.java $(PARSER)
	javac Asgn1.java

$(PARSER): $(JAVACC_FILE)
	# Create the directory if it does not exist
	mkdir -p $(JAVACC_OUTDIR)
	javacc -OUTPUT_DIRECTORY="$(JAVACC_OUTDIR)" $(JAVACC_FILE)
	javac $(JAVACC_OUTDIR)/*.java

clean:
	rm -rf $(JAVACC_OUTDIR) *.class

run:
	java Asgn1 < input
