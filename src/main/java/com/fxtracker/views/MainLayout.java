package com.fxtracker.views;


import com.fxtracker.service.NoteService;
import com.fxtracker.service.UserService;
import com.fxtracker.views.dialog.NoteDialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;

/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {


    private final UserService userService;
    private final NoteService noteService;


    private H1 viewTitle;

    public MainLayout(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
        addFloatingActionButton();
        injectAnimation();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Span appName = new Span("FeedBackBoard");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
        menuEntries.forEach(entry -> {
            if (entry.icon() != null) {
                nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
            } else {
                nav.addItem(new SideNavItem(entry.title(), entry.path()));
            }
        });

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }


    private void addFloatingActionButton() {

        Button fab = new Button(new Icon(VaadinIcon.PLUS));
        fab.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        fab.getElement().setProperty("title", "Новая заметка");

        fab.getStyle()
                .set("position", "fixed")
                .set("bottom", "20px")
                .set("left", "20px")
                .set("borderRadius", "50%")
                .set("width", "56px")
                .set("height", "56px")
                .set("background-color", "rgb(33, 57, 90)")
                .set("padding", "0")
                .set("boxShadow", "rgba(128, 170, 227, 0.27) 0px 0px 6px 4px")
                .set("zIndex", "1000")
                .set("display", "flex")
                .set("align-items", "center")
                .set("animation","pulse 2s infinite")
                .set("justify-content", "center");

        fab.addClickListener(e -> {
            NoteDialog dialog = new NoteDialog(userService, noteService);
            dialog.openDialog();
        });

        getElement().appendChild(fab.getElement());
    }



    private void injectAnimation(){
        String style = """
                
                <style>
                
                @keyframes pulse {
                            0% {
                              box-shadow: 0 0 12px 4px white;
                            }
                            50% {
                              box-shadow: 0 0 20px 10px rgba(255, 255, 255, 0.8);
                            }
                            100% {
                              box-shadow: 0 0 12px 4px white;
                            }
                          }
                </style>
                
                """;
        UI.getCurrent().getElement()
                .executeJs("document.head.insertAdjacentHTML('beforeend', $0)", style);
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}
