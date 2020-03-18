**ZLib**

The mod library used for all of my mods.

**Features**

- Default block classes.
- Helpful classes for registering Blocks, Items, etc.
- Custom registries for registering things like Ores.
- World generation for ores.
- Data support.
- More to come.

**Examples**

Registering blocks

```
@Mod.EventBusSubscriber(modid = Test.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)`
public class ModBlocks extends BlockRegistry {`
 
     public static final Block TEST_BLOCK = register("test:test_block",new ZBlock(Block.Properties.create(Material.WOOD)),Test.GROUP);
 
 }
 ```
Mod class with a data provider
```
@Mod("test")
public class Test extends ZMod {

    public static final String MOD_ID = "test";

    public static final ItemGroup GROUP = new DesignGroup(MOD_ID+".group");

    public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public DesignCraft() {
        super();
        
        addDataProvider(generator -> new ItemModelProvider(generator, MOD_ID, ModBlocks.getBlocks(), ModItems.getItems()));
    
    }

    @Override
    public void onCommonSetup(FMLCommonSetupEvent fmlCommonSetupEvent) {
        proxy.onCommonSetup();
    }

    @Override
    public void onClientSetup(FMLClientSetupEvent fmlClientSetupEvent) {
        proxy.onClientSetup();
    }
```
Registering Ores
```
public class ModOres extends OreRegistry {

    public static final Ore TEST_ORE = register("test:test_ore",new Ore(ModBlocks.TEST_ORE,new Ore.Properties(5,3,10,65)));

}
```
**Gradle**

Coming soon