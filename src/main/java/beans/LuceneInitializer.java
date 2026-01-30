package beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import api.engine.BDeConfig;
import api.text.LuceneService;

@ManagedBean(eager = true)
@ApplicationScoped
public class LuceneInitializer  {

    @PostConstruct
    public void init() {
        try {
			new LuceneService(BDeConfig.getInstance()).buildIndex();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

