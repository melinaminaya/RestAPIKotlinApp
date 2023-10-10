window.addEventListener('load', () => {
    document.querySelectorAll('span.copy-icon').forEach(element => {
        element.addEventListener('click', (el) => copyElementsContentToClipboard(element));
    })

    document.querySelectorAll('span.anchor-icon').forEach(element => {
        element.addEventListener('click', (el) => {
            if(element.hasAttribute('pointing-to')){
                const location = hrefWithoutCurrentlyUsedAnchor() + '#' + element.getAttribute('pointing-to')
                copyTextToClipboard(element, location)
            }
        });
    })
})

const copyElementsContentToClipboard = (element) => {
    const selection = window.getSelection();
    const range = document.createRange();
    range.selectNodeContents(element.parentNode.parentNode);
    selection.removeAllRanges();
    selection.addRange(range);

    copyAndShowPopup(element,  () => selection.removeAllRanges())
}

const copyTextToClipboard = (element, text) => {
    var textarea = document.createElement("textarea");
    textarea.textContent = text;
    textarea.style.position = "fixed";
    document.body.appendChild(textarea);
    textarea.select();

    copyAndShowPopup(element, () => document.body.removeChild(textarea))
}

const copyAndShowPopup = (element, after) => {
    try {
        document.execCommand('copy');
        element.nextElementSibling.classList.add('active-popup');
        setTimeout(() => {
            element.nextElementSibling.classList.remove('active-popup');
        }, 1200);
    } catch (e) {
        console.error('Failed to write to clipboard:', e)
    }
    finally {
        if(after) after()
    }
}

const hrefWithoutCurrentlyUsedAnchor = () => window.location.href.split('#')[0]

var autotracApi = "AutotracApi";

var elements = document.getElementsByClassName("library-name--link");

// Define the text to replace
var newText = "Documentação da API de Integração";

// Loop through all elements and replace the text
for (var i = 0; i < elements.length; i++) {
    elements[i].textContent = newText;
}

var breadcrumbs = document.querySelector(".breadcrumbs");

if (breadcrumbs) {
    // Get the first <a> element inside the breadcrumbs
    var firstLink = breadcrumbs.querySelector("a:first-child");

    // Check if the first <a> element exists
    if (firstLink) {
        // Set its text to "Api"
        firstLink.textContent = "API";
    }

    var referredLink = findElementWithText(breadcrumbs, 'br.com.autotrac.testnanoclient');

    if (referredLink) {
        var oldText = referredLink.textContent;
        var newText = "";

        if (oldText.includes("br.com.autotrac.testnanoclient.consts")) {
            newText = oldText.replace("br.com.autotrac.testnanoclient.consts", "Constantes");
        } else if (oldText.includes("br.com.autotrac.testnanoclient.dataRemote")) {
            newText = oldText.replace("br.com.autotrac.testnanoclient.dataRemote", "DataClasses");
        } else if (oldText.includes("br.com.autotrac.testnanoclient.di")) {
            newText = oldText.replace("br.com.autotrac.testnanoclient.di", "EndpointGenerico");
        }

        if (newText !== "") {
            referredLink.textContent = newText;
        }
    }
    
}
function findElementWithText(parentElement, searchText) {
    var elements = parentElement.querySelectorAll('*');
    for (var i = 0; i < elements.length; i++) {
      if (elements[i].textContent.includes(searchText)) {
        return elements[i];
      }
    }
    return null;
}