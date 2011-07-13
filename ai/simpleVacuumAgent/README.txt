Prequisites:
-Get maven
-Do something similar to this in your .emacs file:
(add-to-list 'load-path "~/.emacs.d/")
(require 'package)
(add-to-list 'package-archives
	     '("marmalade" . "http://marmalade-repo.org/packages/"))

;path to elpa packages
(add-to-list 'load-path "~/.emacs.d/elpa/slime-20100404.1/")
(add-to-list 'load-path "~/.emacs.d/elpa/slime-repl-20100404/")
;superior lisp interaction mode
(require 'slime)
(eval-after-load 'slime '(setq slime-protocol-version 'ignore))
(slime-setup '(slime-repl))

(add-to-list 'load-path "~/.emacs.d/elpa/clojure-mode-1.8.1/") 
(require 'clojure-mode)

Running a REPL loaded with the code:
To run a Clojure REPL server(from the top level directory containing the pom.xml): 
$>mvn clojure:swank

To connect via Emacs:
M-x slime-connect

The pom.xml was got from here:
    - http://dev.clojure.org/display/doc/Getting+Started+with+Maven
